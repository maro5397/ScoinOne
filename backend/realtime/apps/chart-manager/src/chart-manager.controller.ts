import { Controller, Get } from '@nestjs/common';
import { ChartManagerService } from './chart-manager.service';

@Controller()
export class ChartManagerController {
  constructor(private readonly chartManagerService: ChartManagerService) {}

  @Get()
  getHello(): string {
    return this.chartManagerService.getHello();
  }
}
