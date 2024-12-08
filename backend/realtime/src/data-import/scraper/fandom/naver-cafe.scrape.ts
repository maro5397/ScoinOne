import { chromium } from 'playwright';
import { Injectable } from '@nestjs/common';

@Injectable()
export class NaverCafeScrape {
  async getIntroduceData(cafeId: string) {
    const browser = await chromium.launch();
    const page = await browser.newPage();
    await page.goto(`https://cafe.naver.com/CafeProfileView.nhn?clubid=${cafeId}`);

    const data1 = await page.locator('//*[@id="main-area"]/div/table/tbody/tr[14]/td/span[1]').textContent();
    const data2 = await page.locator('//*[@id="main-area"]/div/table/tbody/tr[14]/td/span[2]').textContent();
    const data3 = await page.locator('//*[@id="main-area"]/div/table/tbody/tr[14]/td/span[3]').textContent();

    await browser.close();

    return { data1, data2, data3 };
  }
}
