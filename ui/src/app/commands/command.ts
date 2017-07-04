export interface Command<T> {

  getName(): string;

  execute(payload: T): any;
}
