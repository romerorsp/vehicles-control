import { Command } from 'app/commands/command';
import { Injectable } from '@angular/core';

@Injectable()
export class CommandsMappingService {

  private commands = new Map<string, Command<any>>();

  constructor() { }

  fromName(name: string): Command<any> {
    return this.commands.has(name) ? this.commands.get(name) : null;
  }

  addCommand(command: Command<any>): void {
    this.commands.set(command.getName(), command);
  }
}
