export const environment = {
  production: true,
  httpApiUrl: '',
  websocketApiUrl(): string {
    return `ws://${window.location.host}`;
  }
};
