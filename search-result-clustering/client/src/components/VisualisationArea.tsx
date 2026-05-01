import React, { useState } from "react";
import styles from "./VisualisationArea.module.css";
import BubbleChart from "./BubbleChart";

const VisualisationArea: React.FC = () => {
  return (
    <div className={styles.VisualisationArea}>
      <BubbleChart />
    </div>
  );
};

export default VisualisationArea;
