import axios, { AxiosInstance } from "axios";
import ResponseData from "@/api/models/ResponseData";
import { ConstantsDomainAndApis } from "@/api/ConstantsDomainAndApi";

const http: AxiosInstance = axios.create({
  baseURL: ConstantsDomainAndApis.getStarWarsUri(),
  headers: {
    "Content-type": "application/json",
    Accept: "application/json",
  },
});

export type Options = { headers?: Record<string, any>; code?: number };
export type APIResponse = [null, ResponseData, Options?] | [Error, Options?];

export async function getStarWarsDetails(
  type: string,
  name: string
): Promise<APIResponse> {
  try {
    const { data, headers } = await http.get<ResponseData>(
      `details?type=${type}&name=${name}`
    );
    return [null, data, { headers }];
  } catch (error) {
    console.error(error);
    return [error, error.response?.status];
  }
}
