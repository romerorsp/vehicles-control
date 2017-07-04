import { Command } from './commands/command';
import { Injectable } from '@angular/core';

@Injectable()
export class CommandsMappingService {

  constructor(private commands: Map<string, Command<any>> = new Map<string, Command<any>>()) { }

  fromName(name: string): Command<any> {
    return this.commands.has(name) ? this.commands.get(name) : null;
  }

  addCommand(command: Command<any>): void {
    this.commands.set(command.getName(), command);
  }
}
