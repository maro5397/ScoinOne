import { chromium } from 'playwright';
import { Injectable } from '@nestjs/common';

@Injectable()
export class NaverCafeScraper {
  async getIntroduceData(cafeId: string) {
    const url = `https://cafe.naver.com/CafeProfileView.nhn?clubid=${cafeId}`;

    const browser = await chromium.launch();
    const context = await browser.newContext();
    const page = await context.newPage();
    await page.goto(url);
    const iframe = page.frameLocator('iframe[name="cafe_main"]');

    const memberCount = await iframe
      .locator('xpath=//*[@id="main-area"]/div/table/tbody/tr[14]/td/span[1]')
      .textContent();
    const postCount = await iframe
      .locator('xpath=//*[@id="main-area"]/div/table/tbody/tr[14]/td/span[2]')
      .textContent();
    const visitorCount = await iframe
      .locator('xpath=//*[@id="main-area"]/div/table/tbody/tr[14]/td/span[3]')
      .textContent();

    await browser.close();
    return { memberCount: Number(memberCount), postCount: Number(postCount), visitorCount: Number(visitorCount) };
  }
}
