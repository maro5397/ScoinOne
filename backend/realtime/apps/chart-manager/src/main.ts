import { NestFactory } from '@nestjs/core';
import { ChartManagerModule } from './chart-manager.module';

async function bootstrap() {
  const app = await NestFactory.create(ChartManagerModule);
  await app.listen(process.env.port ?? 3000);
}
bootstrap();
