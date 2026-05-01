import React, { useState, useEffect } from "react";
import axios from "axios";
import styles from "./InputForm.module.css";
import { useData } from "./DataContext";

export interface formDataProps {
  searchString: string;
  algorithm: string;
  kVal?: string;
  isOptK?: string;
}

const InputForm = () => {
  const [enabled, setSubmit] = useState<boolean>(true);
  const [errorMsg, setErrorMsg] = useState<string>("");
  const [formData, setFormData] = useState<formDataProps>({
    algorithm: "kMeans",
    kVal: "1",
    searchString: "",
  });
  const { setData } = useData();
  const [selectedOption, setselectedOption] = useState<number>(1);

  const handleFormDataChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setErrorMsg("")
    const prevFormData = formData;
    const updatedFormData = {
      ...prevFormData,
      [e.target.name]: e.target.value,
    };
    setFormData(updatedFormData);
  };

  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    console.log(e);
    
    setSubmit(false)
    try {
      const response = await axios.post(
        "http://localhost:8080/search",
        formData
      );
      if(response.status == 500) {
        setErrorMsg("Not enough training instances, try another keyword")
      } else {
        console.log("Response from backend:", response);
        setData(response.data);
        setErrorMsg("Hover on the bubble chart to see data points in the cluster")
      }
    } catch (error) {
      setErrorMsg("Not enough training instances, try another keyword")
      console.error("Error submitting form:", error as Error);
    }
    setSubmit(true)
  };

  const isInputValid = () => {
    // Check if searchString is not empty or contains only spaces
    return formData.searchString.trim() !== "";
  };

  const handleClustersInput = (e: React.ChangeEvent<HTMLInputElement>) => {
    console.log(e.target.value)
    setselectedOption(Number(e.target.value));
    handleFormDataChange(e);
  };

  return (
    <div>
      <p> Enter the query and number of clusters </p>
      <form className={styles.input} onSubmit={handleSubmit} onClick={() => {setErrorMsg("")}}>
        <input
          type="text"
          placeholder="Enter a query. eg.: dance"
          name="searchString"
          required
          className={styles.inputSearch}
          onChange={handleFormDataChange}
        />

          <input type="number" name="kVal" min={1} max={15} 
          className={styles.inputSearch2}
          onChange = {handleClustersInput} />
          <button
            className={styles.inputSubmit}
            type="submit"
            disabled={!enabled || !isInputValid()}
          >
            Submit
          </button>
      </form>
      <p style={{color: "red"}}>{errorMsg}</p>
    </div>
  );
};

export default InputForm;
