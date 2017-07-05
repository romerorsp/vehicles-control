import { CommandsMappingService } from './services/commands-mapping.service';
import { ApplicationSocketService } from './services/application-socket.service';
import { VehiclesWSService } from './services/vehicles-w-s.service';
import { SocketService } from './services/socket.service';
import { VehiclesCanvasService } from './services/vehicles-canvas.service';
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpModule } from '@angular/http';
import { FormsModule } from '@angular/forms';

import { MaterialModule,
         MdDialogModule,
         MdToolbarModule,
         MdIconModule,
         MdButtonModule,
         MdCardModule,
         MdTabsModule,
         MdSliderModule,
         MdInputModule } from '@angular/material';
import { AppComponent } from './app.component';
import { CanvasTabComponent } from './canvas-tab/canvas-tab.component';
import { CreateNewFieldDialogComponent } from './create-new-field-dialog/create-new-field-dialog.component';

@NgModule({
  declarations: [
    AppComponent,
    CanvasTabComponent,
    CreateNewFieldDialogComponent
  ],
  entryComponents: [CreateNewFieldDialogComponent],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    MaterialModule,
    MdDialogModule,
    MdToolbarModule,
    MdIconModule,
    MdButtonModule,
    MdCardModule,
    MdTabsModule,
    MdInputModule,
    MdSliderModule,
    HttpModule,
    FormsModule
  ],
  providers: [VehiclesCanvasService,
              SocketService,
              VehiclesWSService,
              ApplicationSocketService,
              CommandsMappingService],
  bootstrap: [AppComponent]
})
export class AppModule { }
