import { chromium } from 'playwright';
import { Injectable } from '@nestjs/common';

@Injectable()
export class NaverCafeScraper {
  async getIntroduceData(cafeId: string) {
    const browser = await chromium.launch();
    const page = await browser.newPage();
    await page.goto(`https://cafe.naver.com/CafeProfileView.nhn?clubid=${cafeId}`);

    const memberCount = await page.locator('//*[@id="main-area"]/div/table/tbody/tr[14]/td/span[1]').textContent();
    const postCount = await page.locator('//*[@id="main-area"]/div/table/tbody/tr[14]/td/span[2]').textContent();
    const visitorCount = await page.locator('//*[@id="main-area"]/div/table/tbody/tr[14]/td/span[3]').textContent();

    await browser.close();

    return { memberCount: memberCount, postCount: postCount, visitorCount: visitorCount };
  }
}
