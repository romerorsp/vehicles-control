import { CommandsMappingService } from 'app/services/commands-mapping.service';
import { VehiclesWSService } from 'app/services/vehicles-w-s.service';
import { SocketService } from 'app/services/socket.service';
import { VehiclesCanvasService } from 'app/services/vehicles-canvas.service';
import { VehiclesService } from 'app/services/vehicles.service';
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
import { DrawVehicleCommandService } from "app/services/draw-vehicle-command.service";

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
              CommandsMappingService,
              VehiclesService,
              DrawVehicleCommandService],
  bootstrap: [AppComponent]
})
export class AppModule { }
