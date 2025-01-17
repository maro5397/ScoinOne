import { Controller, Get } from '@nestjs/common';
import { ScraperManagerService } from './scraper-manager.service';

@Controller()
export class ScraperManagerController {
  constructor(private readonly scraperManagerService: ScraperManagerService) {}

  @Get()
  getHello(): string {
    return this.scraperManagerService.getHello();
  }
}
