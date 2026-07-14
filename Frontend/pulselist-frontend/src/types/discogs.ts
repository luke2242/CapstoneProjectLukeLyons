export interface DiscogsDTO {
  id: number;
  title: string;
  image: string;
  year: string;
  country: string;
}

// Searching returns some different image information, which is why it's seperate from our standard disocgs DTO
export interface DiscogsSearchResultDTO {
  id: number;
  title: string;
  year: string;
  country: string;
  cover_image: string;
  thumb: string;
}

export interface DiscogsSearchResponse {
  results: DiscogsSearchResultDTO[];
}