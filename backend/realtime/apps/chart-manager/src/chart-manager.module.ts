import { Module } from '@nestjs/common';
import { ChartManagerController } from './chart-manager.controller';
import { ChartManagerService } from './chart-manager.service';

@Module({
  imports: [],
  controllers: [ChartManagerController],
  providers: [ChartManagerService],
})
export class ChartManagerModule {}
