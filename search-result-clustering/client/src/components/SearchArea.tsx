import React from "react";
import styles from "./SearchArea.module.css";
import InputForm from "./InputForm";

const SearchArea: React.FC = () => {
  return (
    <div className={styles.SearchArea}>
      <InputForm />
    </div>
  );
};

export default SearchArea;
