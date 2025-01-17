import { Module } from '@nestjs/common';
import { ScraperManagerController } from './scraper-manager.controller';
import { ScraperManagerService } from './scraper-manager.service';

@Module({
  imports: [],
  controllers: [ScraperManagerController],
  providers: [ScraperManagerService],
})
export class ScraperManagerModule {}
