import { Injectable } from '@nestjs/common';
import { chromium } from 'playwright';

@Injectable()
export class GoogleTrendsScrape {
  async getTrends(keyword: string) {
    const url = `https://trends.google.co.kr/trends/explore?date=now 1-H&geo=KR&q=${keyword}&hl=ko`;

    const browser = await chromium.launch();
    const page = await browser.newPage();
    await page.goto(url);

    const lastTwoElements = await page.$$eval(
      '/html/body/div[3]/div[2]/div/md-content/div/div/div[1]' +
        '/trends-widget/ng-include/widget/div/div/ng-include/div/ng-include/div' +
        '/line-chart-directive/div[1]/div/div[1]/div/div/table/tbody/tr',
      (rows) => {
        return rows.slice(-2).map((row) => {
          const time = row.querySelector('td:nth-child(1)').textContent;
          const value = row.querySelector('td:nth-child(2)').textContent;
          return { time, value };
        });
      },
    );

    await browser.close();
    return lastTwoElements;
  }
}
