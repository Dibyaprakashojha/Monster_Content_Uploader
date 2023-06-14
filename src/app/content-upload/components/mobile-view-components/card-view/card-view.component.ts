import { Input, OnInit } from '@angular/core';
import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'mcu-card-view',
  templateUrl: './card-view.component.html',
  styleUrls: ['./card-view.component.scss'],
})
export class CardViewComponent implements OnInit {
  @Input() job: any;


  constructor(private router:Router){

  }

  ngOnInit(): void {
    // console.log(`jobis`, this.job);
  }


  editTheJob(job: any) {
    // this.router.navigate(['./edit-from'], {
    //   queryParams: { jobId: job.job_id },
    // });
    this.router.navigate(['/apps/edit-form'], {
      queryParams: { jobId: job.job_id },
    });
    console.log(job.job_id);
  }
  viewTheJob(job: any) {
    // this.formService.getJobdetailsByJobId(job.job_id).subscribe((data: any) => {
    this.router.navigate(['/apps/view-form'], {
      queryParams: { jobId: job.job_id },
    });
    // });
    console.log(job.job_id);
  }
}
