import { Field } from './../field';
import { Command } from './command';
export class NewFieldCommand implements Command<Field> {

  constructor (private name: string, private fields: Array<Field>) {
  }
  execute(payload: Field) {
    this.fields.push(payload);
  }

  getName(): string {
    return this.name;
  }
}
