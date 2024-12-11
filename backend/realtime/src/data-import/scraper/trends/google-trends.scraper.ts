import { Injectable } from '@nestjs/common';
import { chromium } from 'playwright';

@Injectable()
export class GoogleTrendsScraper {
  // data per 8 minute
  // Chart data may not be received in some cases
  async getTrends(keyword: string) {
    const url = `https://trends.google.co.kr/trends/explore?date=now%201-d&geo=KR&q=${keyword}&hl=ko`;

    const browser = await chromium.launch();
    const context = await browser.newContext();
    const page = await context.newPage();
    await page.goto(url);
    await page.waitForLoadState('networkidle');
    await page.reload();
    await page.waitForLoadState('networkidle');

    const errorCodeTag = '//*[@id="af-error-container"]/p[1]/b';
    const errorCodeLocator = page.locator(`xpath=${errorCodeTag}`);
    if ((await errorCodeLocator.count()) > 0 && (await errorCodeLocator.textContent()).includes('429')) {
      await browser.close();
      throw new Error('HTTP 429: Too Many Requests');
    }

    for (let i = 0; i < 5; i++) {
      const tdElements = await page.locator('tbody tr').evaluateAll((rows) => {
        const lastTwoRows = rows.slice(-2);
        return lastTwoRows.map((row) => {
          const tds = row.querySelectorAll('td');
          if (tds.length == 2) {
            return { period: tds[0].textContent.trim(), ratio: Number(tds[1].textContent.trim()) };
          }
        });
      });
      if (tdElements.length == 2) {
        await browser.close();
        return tdElements;
      }
      await page.reload();
      await page.waitForLoadState('networkidle');
    }
    return [
      { period: null, ratio: 0 },
      { period: null, ratio: 0 },
    ];
  }
}
