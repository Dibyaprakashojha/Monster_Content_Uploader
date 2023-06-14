import { AfterViewInit, Component, ElementRef, EventEmitter, HostListener, Input, OnInit, Output } from '@angular/core';
import { NotificationServiceService } from '../../services/notification-service.service';
import { MatDialog } from '@angular/material/dialog';
import { OtmmService } from '../../services/otmm.service';
declare const qds_otmm:any

@Component({
  selector: 'mcu-file-upload',
  templateUrl: './file-upload.component.html',
  styleUrls: ['./file-upload.component.scss']
})
export class FileUploadComponent implements OnInit, AfterViewInit {
  @Input() fileFormats!: string[];
  @Input() maxFiles!: number;
  @Input() maxFileSize!: number;
  @Input() isRevisionUpload!: boolean;
  @Input() fileToRevision: any;
  @Input() fileContent: any;

  @Output() public filesAdded: EventEmitter<File[]> = new EventEmitter<File[]>();
  @Output() public queuedFiles: EventEmitter<File[]> = new EventEmitter<File[]>();
  thumbnailFormats = ['image/jpeg', 'image/png', 'image/jpg', 'image/gif'];
  private htmlElement: HTMLElement;
  selectedFiles:any = [];
  isQDSUpload = false;

  constructor(
    private element: ElementRef,
    private notificationService: NotificationServiceService,
    private dialog: MatDialog,
    private otmmService:OtmmService ,
) {
    this.htmlElement = this.element.nativeElement;
}

getTypeOf(obj: any) {
  return typeof obj;
}
openQDSFileChooser() {
  const that = this;
  qds_otmm.connector.openFileChooser((fileInfoList: any) => {


      let fileObj;
      const transferFileObjectList = [];


      if (!fileInfoList) {
          return;
      }


      if (!Array.isArray(fileInfoList)) {
          fileInfoList = [fileInfoList];
      }


      for (const fileInfo of fileInfoList) {
          let fileObj :any= {};
          fileObj.name = fileInfo.name;
          fileObj.path = fileInfo.path;
          fileObj.size = fileInfo.size;
          fileObj.type = fileInfo.type;
          fileObj.url = fileInfo.thumbnail;
          fileObj.assetUrl = fileInfo.thumbnail;
          fileObj.width = fileInfo.imageWidth;
          fileObj.height = fileInfo.imageHeight;
          transferFileObjectList.push(fileObj);
      }


      that.processFileSelection(transferFileObjectList);


  });
}

calculateSelectedFileSize(): number {
  let fileSize = 0;
  if (Array.isArray(this.selectedFiles) && this.selectedFiles.length > 0) {
      for (const selectedFile of this.selectedFiles) {
          if (selectedFile && selectedFile.size
              && selectedFile.size != null && selectedFile.size > 0) {
              fileSize += selectedFile.size;
          }
      }
  }
  return fileSize;
}
formatBytes(bytes: number): string {
  if (bytes === 0) { return '0 Bytes'; }
  const k = 1024;
  const dm = 2;
  const sizes = ['Bytes', 'KB', 'MB', 'GB', 'TB', 'PB', 'EB', 'ZB', 'YB'];
  const i = Math.floor(Math.log(bytes) / Math.log(k));
  return parseFloat((bytes / Math.pow(k, i)).toFixed(dm)) + ' ' + sizes[i];
}
checkFilesSizeCosntraint(files: File[]): boolean {
  if (!this.maxFileSize) {
      return true;
  }
  let validity = true;
  let currentSelectedFileSize = 0;
  if (files && files.length) {
      for (const file of files) {
          if (file.size && file.size != null && file.size > 0) {
              currentSelectedFileSize += file.size;
          }
      }
      if (currentSelectedFileSize <= this.maxFileSize) {
          const existingFileSize = this.calculateSelectedFileSize();
          if (currentSelectedFileSize + existingFileSize <= this.maxFileSize) {
              validity = true;
          } else {
              validity = false;
          }
      } else {
          validity = false;
      }
  }
  if (!validity) {
      this.notificationService.warn('Cannot upload file(s) of size more than ' + Math.floor((this.maxFileSize / 1000 / 1000)) + 'MB.');
  }
  return validity;
  
}
filterFiles(currentSelectedFiles: string | any[] | null, existingFiles: any[]): File[] {
  const validFiles: any[] = [];
  const duplicates = [];
  if (currentSelectedFiles && currentSelectedFiles != null && currentSelectedFiles.length && currentSelectedFiles.length > 0) {
      for (const currentSelectedFile of currentSelectedFiles) {
          let alreadyExist = false;
          const currentFile = currentSelectedFile;
          for (const existingFile of existingFiles) {
              if (currentFile.name === existingFile.name) {
                  alreadyExist = true;
                  duplicates.push(currentSelectedFile);
                  break;
              }
          }
          if (!alreadyExist) {
              validFiles.push(currentSelectedFile);
          }
      }
      if (duplicates.length > 0) {
          this.notificationService.error('File was not added because its name matches an existing file.');
      }
      return validFiles;
  } else {
      return validFiles;
  }
}


getFilesWebkitDataTransferItems(dataTransferItems: any): Promise<File[]> {
  const files: File[] = [];
  function traverseFileTreePromise(item: { isFile: any; file: (arg0: (file: any) => void) => void; isDirectory: any; createReader: () => any; name: string; }, path = '') {
      return new Promise(resolve => {
          if (item.isFile) {
              item.file((file: any) => {
                  file.filepath = path + file.name;
                  files.push(file);
                  resolve(file);
              });
          } else if (item.isDirectory) {
              const dirReader = item.createReader();
              dirReader.readEntries((entries: any) => {
                  const entriesPromises = [];
                  for (const entr of entries) {
                      entriesPromises.push(traverseFileTreePromise(entr, path + item.name + '/'));

                    }
                    resolve(Promise.all(entriesPromises));
                });
            }
        });
    }
    return new Promise((resolve, reject) => {
        const entriesPromises = [];
        for (const it of dataTransferItems) {
            entriesPromises.push(traverseFileTreePromise(it.webkitGetAsEntry()));
        }
        Promise.all(entriesPromises).then(entries => {
            resolve(files);
        });
    });
}



createFileObj(file: { data: any; url: any; } | null, data: string |  null) {
  if (file && file !== null) {
      if (data && data !== null) {
          file.data = data.split('base64,')[1];
          file.url = data;
      }
      return file;
  } else {
      return null;
  }
}
checkNumberOfFileConstraint(files: File[]): boolean {
  if (!this.maxFiles || files.length === 0) {
      return true;
  }
  let isValid = true;
  const currentSelectedFilesCount = files.length;
  const alreadySelectedFilesCount = this.selectedFiles.length;
  if (this.maxFiles === alreadySelectedFilesCount) {
      isValid = false;
  } else if (this.maxFiles < (currentSelectedFilesCount + alreadySelectedFilesCount)) {
      isValid = false;
  }
  if (!isValid) {
      if (alreadySelectedFilesCount === 0) {
          this.notificationService.warn('You can select only a maximum '
              + this.maxFiles + ' file(s), but you are trying to select ' + currentSelectedFilesCount + ' file(s).');
      } else if (alreadySelectedFilesCount > 0) {
          this.notificationService.warn('You can select only a maximum ' + this.maxFiles
              + ' file(s), You already selected ' + alreadySelectedFilesCount + ' file(s).');
      } else {
          this.notificationService.warn('You you can select only a maximum ' + this.maxFiles + ' file(s).');
      }
  }
  return isValid;
}
checkForSpecialCharacter(files: File[]) {
  if (files) {
      const filesWithoutSpecialCharacter: File[] = [];
      const filesWithSpecialCharacter: File[] = [];
      for (const fileInfo of files) {
          (fileInfo && fileInfo.name && (fileInfo.name.indexOf('$') >= 0 || fileInfo.name.indexOf('\'') >= 0)) ?
              filesWithSpecialCharacter.push(fileInfo) : filesWithoutSpecialCharacter.push(fileInfo);
      }
      if (filesWithSpecialCharacter.length > 0) {
        //   const dialogRef = this.dialog.open(ConfirmationModalComponent, {
        //       width: '40%',
        //       disableClose: true,
        //       data: {
        //           message: 'The special character $ and \' will be replaced with _.Do you want to continue?',


        //           submitButton: 'Yes',
        //           cancelButton: 'No'
        //       }
        //   });
        //   dialogRef.afterClosed().subscribe(result => {
        //       if (result && result.isTrue) {
        //           this.processFileSelection([...filesWithoutSpecialCharacter, ...filesWithSpecialCharacter]);
        //       }
        //   });
      } 
      else {
          this.processFileSelection(files);
      }
  }
}

processFileSelection(files: any[]) {
  if ((this.isQDSUpload && this.checkNumberOfFileConstraint(files)) ||
      (this.checkNumberOfFileConstraint(files) && this.checkFilesSizeCosntraint(files))) {
      const validFiles = this.filterFiles(files, this.selectedFiles);
      this.handleFileProcessing(validFiles);
  }
}


handleFileProcessing(validFiles:  any[]) {
  this.filesAdded.next(validFiles);
  for (let i = 0; i < validFiles.length; i++) {
      const fileObj: any = validFiles[i];
      const objectUrl = fileObj.assetUrl;


      if (objectUrl instanceof Promise) {
          objectUrl
              .then(obj => {
                  fileObj.width = obj.imageWidth,
                      fileObj.height = obj.imageHeight,
                      fileObj.url = obj.thumbnail,
                      fileObj.assetUrl = obj.thumbnail;
              })
              .catch(err => {
                  //console.log('Unable to generate thumbnail :' + err);
              });
      }


      if (fileObj && fileObj != null) {
          if (this.thumbnailFormats.indexOf(fileObj.type) !== -1 && i < 10) {
              if (!this.isQDSUpload) {
                  const reader = new FileReader();
                  reader.onload = (e) => {
                      const data:any = reader.result;
                      const obj = this.createFileObj(fileObj, data);
                      if (obj && obj !== null) {
                          this.selectedFiles.push(obj);
                      }
                  };
                  reader.readAsDataURL(fileObj);
              } else {
                  if (!fileObj.url || typeof fileObj.url === 'object') {
                      //fileObj.icon = this.assetFileConfigService.findIconByName(fileObj.name);
                      fileObj.icon = this.otmmService.findIconByName(fileObj.name)
                  }
                  this.selectedFiles.push(fileObj);
              }
          } else {
              //fileObj.icon = this.assetFileConfigService.findIconByName(fileObj.name);
              fileObj.icon = this.otmmService.findIconByName(fileObj.name)
              this.selectedFiles.push(fileObj);
          }
      }
  }
  this.queuedFiles.next(this.selectedFiles);
}


@HostListener('drop', ['$event'])
public onDrop(event: any): any {

  const items = event.dataTransfer.items;
  this.getFilesWebkitDataTransferItems(items).then(files => {
      this.checkForSpecialCharacter(files);
  });
  event.preventDefault();
  event.stopPropagation();
  this.element.nativeElement.value = '';
}


@HostListener('dragover', ['$event'])
public onDropOver(event: any): any {
 
  event.preventDefault();
}

public onFileSelectionChange(event: any): any {
  const files = event.target.files;
  this.checkForSpecialCharacter(files);
  event.target.value = '';
}


removeAll() {
  this.selectedFiles = [];
  this.queuedFiles.next(this.selectedFiles);
}


removeFile(fileObj: any, index: number, event:any ) {

    if(event){
        event.stopPropagation()
    }

    // console.log('rmoved files in angualr ')
    // if(event.currentTarget as HTMLButtonElement  ){
    // event.stopPropagation();
// }
  this.selectedFiles.splice(index, 1);
  this.queuedFiles.next(this.selectedFiles);
}



createQDSDropArea() {
  const that = this;
  const dropzoneEl = this.element.nativeElement.querySelector('div.acron-file-upload-wrapper');
  dropzoneEl.addEventListener('qds_dragdrop', (event: { detail?: any; }) => {
      event = event || {};
      const eventType = (event.detail || {}).qdsEventType;
      if (eventType === 'dragleave' || eventType === 'addfile') {
      }
  });


  qds_otmm.connector.createDropArea(dropzoneEl, (fileInfoList: any) => {
      let fileObj;
      const transferFileObjectList = [];
      if (!fileInfoList) {
          return;
      }
      if (!Array.isArray(fileInfoList)) {
          fileInfoList = [fileInfoList];
      }

      for (const fileInfo of fileInfoList) {
        let fileObj:any={};
          fileObj.name = fileInfo.name;
          fileObj.path = fileInfo.path;
          fileObj.size = fileInfo.size;
          fileObj.type = fileInfo.type;
          fileObj.url = fileInfo.thumbnail;
          fileObj.assetUrl = fileInfo.thumbnail;
          fileObj.width = fileInfo.imageWidth;
          fileObj.height = fileInfo.imageHeight;
          transferFileObjectList.push(fileObj);
      }
      that.processFileSelection(transferFileObjectList);
  });
}


renameFileName(fileName: string): string {
  return fileName.replace(new RegExp('\''), '_').split('$').join('_');
}

ngOnInit() {
  this.selectedFiles = [];
  //for drag and drop from folderview
  if(this.fileContent) {
    this.getFilesWebkitDataTransferItems(this.fileContent).then(files => {
        this.checkForSpecialCharacter(files);
    });
  }
}


ngAfterViewInit() {
  if (this.isQDSUpload) {
      this.createQDSDropArea();
  }
}
}




