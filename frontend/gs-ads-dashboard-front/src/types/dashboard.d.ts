declare module '@/api/dashboard' {
    export function getAnomalyProducts(): Promise<unknown>;
    export function getScheduleCount(): Promise<unknown>;
    export function getCount(): Promise<unknown>;
  }
  