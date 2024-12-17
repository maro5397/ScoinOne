import { HttpService } from '@nestjs/axios';
import { ChzzkLiveChatScraper } from '../../../../../scraper/live/chzzk/chzzk-live-chat.scraper';

(async function () {
  const httpService = new HttpService();

  const scraper = new ChzzkLiveChatScraper(httpService);

  try {
    const streamerId = 'a6c4ddb09cdb160478996007bff35296';
    const liveChatInfo = await scraper.getLiveChats(streamerId);
    console.log('Live Chat Info:', liveChatInfo);
  } catch (error) {
    console.error('Error fetching live chat info:', error.message);
  }
})();
