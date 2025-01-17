import { Injectable } from '@nestjs/common';

@Injectable()
export class ChartManagerService {
  getHello(): string {
    return 'Hello World!';
  }
}
