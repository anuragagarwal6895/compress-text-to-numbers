;; declare namespace
(ns compress
  (:require [clojure.string :as str])
  (:import (java.io FileReader BufferedReader FileNotFoundException)))

;; function to display list of files
(defn listDisplay []
  (let [files (file-seq (clojure.java.io/as-file "."))]
    (println "\nFile list:")
    (doseq [file files]
      (println "* ./" (.getName file)))))

;; function to display file content
(defn contentDisplay []
  (println "\nPlease enter a file-name =>")
  (let [file-name (read-line)]
    (try
      (let [contents (slurp file-name)]
        (println contents))
      (catch FileNotFoundException _
        (println (format "Oops: specified file '%s' does not exist" file-name))))))

;; function to compress a file
(defn compression []
  (println "\nEnter a file-name to compress =>")
  (let [file-name (read-line)
        ffile "frequency.txt"
        output-file (str file-name ".ct")]
    (try
      (let [contents (slurp file-name)
            fmap (atom {})
            counter (atom 0)]

        ;; Read the frequency file and store in map
        (with-open [reader (clojure.java.io/reader ffile)]
          (let [line (slurp ffile)
                words (distinct (clojure.string/split line #" "))]
            (doseq [word words]
              (let [lower-case-word (clojure.string/lower-case word)]
                (swap! fmap #(if (contains? %1 lower-case-word)
                                        %1
                                        (assoc %1 lower-case-word @counter))))
              (swap! counter inc))))

        ;; Compression logic
        (let [compressText (map #(if (re-matches #"\d+" %)
                                         (str "@" % "@")
                                         (or (@fmap (clojure.string/lower-case %)) %))
                                      (clojure.string/split contents #"(?<=\W)|(?=\W)"))]
          (spit output-file (clojure.string/join " " compressText))
          (println (format "Compression complete."))))

      (catch FileNotFoundException _
        (println (format "Oops: specified file '%s' does not exist" file-name))))))

;; function to uncompress a file
(defn numberHandler [word]
  (and (.startsWith word "@")
       (.endsWith word "@")
       (> (count word) 2)))

(defn digitHandler [word]
  (if (= (every? #(Character/isDigit %) word) true)
    true
    nil))

(defn getDistinctValues [text]
  distinct text)

(defn zipTexts [fileName]
  (def content (slurp fileName))
  (def splitText1 (clojure.string/split content #" "))
  (def splitText2 (distinct splitText1))
  (zipmap (range) splitText2))

(def fmaps (zipTexts "frequency.txt"))
	
	;; function to handle special charatcers and symbols
(defn specialCharHandler [var]
	(when (or (= var ",") (= var "!") (= var "?") (= var ")") (= var "]"))
        (print var ""))
    (when (or (= var "(") (= var "[") (= var "@") (= var "$"))
        (print "" var)
        (def no-space true)))
	
	;; function to handle extra-spaces
(defn spaceHandler [spaceVar]
	(when (not= no-space true)
        (print "" spaceVar))
    (when (= no-space true)
        (print spaceVar)
        (def no-space false)))
	
	;; function to handle UpperCase Character for Text
(defn upperCaseHandler [start spaceVar]
	(when (= start true)
        (print (str/capitalize spaceVar))
        (def start "false")))
	
	;; function to handle digits in Original Text
(defn randomDigitHandler [var]
	(when (= (digitHandler var) nil)
    (when (= (some #(Character/isLetter %) var) true)
          (print "" var))
    (when (= (numberHandler var) true)
          (print "" (subs var 1 (- (count var) 1))))
        (specialCharHandler var)
    (when (= var "-")
          (print "" var ""))

    (when (= var ".")
          (print var "")
          (def start true))))

	;;Main function to perform decompression
(defn decompressMain [splitContent]
	(def start true)
    (doseq [var splitContent]
          (when (not (empty? var))
            (randomDigitHandler var)
            (when (= (digitHandler var) true)
              (def spaceVar (get fmaps (Integer/parseInt var)))
                (when (= start "false")
                  (spaceHandler spaceVar))
                (upperCaseHandler start spaceVar)))))

(defn uncompression []
    (println "Enter a file name to uncompress => ")
    (def fileName (read-line))
    (def fileContents (slurp fileName))
    (decompressMain (str/split fileContents #" ")))