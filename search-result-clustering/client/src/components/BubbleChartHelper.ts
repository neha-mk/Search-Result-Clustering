import { Clusters, Document } from "./ResponseInterface";

interface ConvertedDataPackedBubbleFormatProps {
  data: {
    name: string;
    value: number;
    content: string;
  }[];
  name: string;
}

function convertedDataPackedBubbleFormat(data: Clusters) {
  const convertedData: ConvertedDataPackedBubbleFormatProps[] = [];

  for (const clusterKey in data) {
    const clusterData: Document[] = data[clusterKey];
    const convertedCluster: ConvertedDataPackedBubbleFormatProps = {
      data: [],
      name: `Cluster ${clusterKey}`,
    };

    clusterData.forEach((item: Document) => {
      const convertedItem = {
        name: item.docName,
        value: item.score,
        content: item.docExtract,
      };
      convertedCluster.data.push(convertedItem);
    });

    convertedData.push(convertedCluster);
  }

  return convertedData;
}

export default convertedDataPackedBubbleFormat;
