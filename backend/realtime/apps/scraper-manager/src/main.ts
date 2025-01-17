import { NestFactory } from '@nestjs/core';
import { ScraperManagerModule } from './scraper-manager.module';

async function bootstrap() {
  const app = await NestFactory.create(ScraperManagerModule);
  await app.listen(process.env.port ?? 3000);
}
bootstrap();
