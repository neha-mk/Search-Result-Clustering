export interface Document {
  score: number;
  cluster: number;
  docName: string;
  docExtract: string;
  coordinates: [number, number];
}

export interface Clusters {
  [key: string]: Document[];
}
