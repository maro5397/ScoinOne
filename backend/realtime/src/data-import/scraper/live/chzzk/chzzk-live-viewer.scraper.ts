import { Injectable } from '@nestjs/common';
import { ChzzkClient } from './client';

@Injectable()
export class ChzzkLiveViewerScraper {
  async getViewerCount(streamerId: string) {
    try {
      const client = new ChzzkClient();
      const liveStatus = await client.live.status(streamerId);
      if (liveStatus.status === 'OPEN') {
        const viewerCount = liveStatus.concurrentUserCount;
        return { live: true, viewerCount: viewerCount };
      }
      return { live: false, viewerCount: 0 };
    } catch (error) {
      throw error;
    }
  }
}
