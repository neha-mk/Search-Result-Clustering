import React from "react";
import SearchArea from "./SearchArea";
import VisualisationArea from "./VisualisationArea";
import styles from "./Main.module.css";
import { DataProvider } from "./DataContext";

const Main: React.FC = () => {
  return (
    <div className={styles.splitContainer}>
      <DataProvider>
        <SearchArea />
        <VisualisationArea />
      </DataProvider>
    </div>
  );
};

export default Main;
