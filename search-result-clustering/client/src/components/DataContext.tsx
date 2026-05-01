import React, { createContext, useContext, useState, ReactNode } from "react";
import { Clusters } from "./ResponseInterface";

interface DataContextProps {
  fetchedData: Clusters | undefined;
  setData: (data: Clusters) => void;
}

const DataContext = createContext<DataContextProps | undefined>(undefined);

interface DataProviderProps {
  children: ReactNode;
}

export const DataProvider: React.FC<DataProviderProps> = ({ children }) => {
  const [fetchedData, setFetchedData] = useState<Clusters>();

  const setData = (data: Clusters) => {
    setFetchedData(data);
  };

  return (
    <DataContext.Provider value={{ fetchedData, setData }}>
      {children}
    </DataContext.Provider>
  );
};

export const useData = (): DataContextProps => {
  const context = useContext(DataContext);
  if (!context) {
    throw new Error("useData must be used within a DataProvider");
  }
  return context;
};
