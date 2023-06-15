import { Component, OnInit, OnDestroy, ChangeDetectorRef } from '@angular/core';
import { Subscription } from 'rxjs';
import { LoaderState } from './loader';
import { LoaderService } from './loader.service';

@Component({
    selector: 'app-loading',
    templateUrl: './loading.component.html',
    styleUrls: ['./loading.component.scss']
})

export class LoadingComponent implements OnInit, OnDestroy {

    show = false;
    private subscription!: Subscription;
    constructor(
        private loaderService: LoaderService,
        private changeDetectorRef: ChangeDetectorRef
    ) { }

    ngOnInit() {
        this.subscription = this.loaderService.loaderState
            .subscribe((state: LoaderState) => {
                this.show = state.show;
                this.changeDetectorRef.detectChanges();
            });
            
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }
}
