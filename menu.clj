;; declare namespace
(ns menu
  (:require [compress :refer :all]))

;; driver menu
(defn mainMenu []
  (println "\n*** Compression Menu ***")(println "----------------------------")(println "1. Display list of files")(println "2. Display file contents")(println "3. Compress a file")(println "4. Uncompress a file")(println "5. Exit"))

;; function to read user-option
(defn readUserOption []
  (print "\nEnter an option? ")
  (flush)
  (let [option (read-line)]
    (if (re-find #"\d" option)
      (Integer/parseInt option)
      (readUserOption))))

;; function to process user-option
(defn ruleUserOption [option]
  (cond
    (= option 1) (listDisplay)
    (= option 2) (contentDisplay)
    (= option 3) (compression)
    (= option 4) (uncompression)
    (= option 5) (println "Exiting...")
    :else (println "Oops: Invalid option")))

;; function to run main-menu
(defn driverMainMenu []
  (loop []
    (mainMenu)
    (let [option (readUserOption)]
      (ruleUserOption option)
      (if (not= option 5)
        (recur)))))

(driverMainMenu)