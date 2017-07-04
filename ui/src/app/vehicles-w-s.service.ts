import { Field } from './field';
import { Injectable } from '@angular/core';
import { Http } from '@angular/http';
import { environment } from 'environments/environment';

import 'rxjs/add/operator/toPromise';

@Injectable()
export class VehiclesWSService {

  private fieldsUrl = environment.restServerAddress + 'fields';
  private addFieldUrl = environment.restServerAddress + 'field';
  private headers = new Headers({'Content-Type': 'application/json'});

  constructor(private http: Http) {}

  getFields(): Promise<Array<Field>> {
    return this.http.get(this.fieldsUrl, this.headers)
                    .toPromise()
                    .then(response => response.json() as Array<Field>)
                    .catch(this.handleError);
  }

  addField(field: Field): Promise<boolean> {
    return this.http.post(this.addFieldUrl, field, this.headers)
                    .map(res => {
                      if (res) {
                        return { status: res.status};
                      }
                      return { status: -1};
                    })
                    .toPromise()
                    .then(response => response.status === 201);
  }

  private handleError(error: any): Promise<any> {
    console.error('An error occurred', error); // for demo purposes only
    return Promise.reject(error.message || error);
  }
}
