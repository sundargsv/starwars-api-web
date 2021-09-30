export class ConstantsDomainAndApis {
  // public static ApiGatewayUrl = process.env.VUE_APP_API_URL;
  public static ApiGatewayUrl = "http://localhost:8080";

  public static getBaseUrl(): string {
    return ConstantsDomainAndApis.ApiGatewayUrl;
  }

  public static getStarWarsUri(): string {
    return this.getBaseUrl() + "/starwars";
  }
}
