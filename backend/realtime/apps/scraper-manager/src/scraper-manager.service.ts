import { Injectable } from '@nestjs/common';

@Injectable()
export class ScraperManagerService {
  getHello(): string {
    return 'Hello World!';
  }
}
