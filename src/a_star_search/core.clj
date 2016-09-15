(ns a-star-search.core
  (:require [search.algorithms :as alg]
            [problems.n-puzzle :as np]
            [problems.sudoku :as su :refer [b]])
  (:gen-class))

(defn print-results [result path costs goal-state]
  (do
    (println "GOAAAAAAAAAAAAAAAAAALLLLLLLLLLLL!")
    (loop [lst (partition 9 goal-state)]
      (if (not-empty lst)
        (do (println (first lst))
          (recur (rest lst)))))
    (println (count goal-state) "\n")
    (println "Don't try to print the path.")))


(defn -main
  "Search for a hard-coded N-puzzle target state."
  [& args]
  (let [start-state [5 3 (b) (b) 7 (b) (b) (b) (b)
                      6 (b) (b) 1 9 5 (b) (b) (b)
                      (b) 9 8 (b) (b) (b) (b) 6 (b)
                      8 (b) (b) (b) 6 (b) (b) (b) 3
                      4 (b) (b) 8 (b) 3 (b) (b) 1
                      7 (b) (b) (b) 2 (b) (b) (b) 6
                      (b) 6 (b) (b) (b) (b) 2 8 (b)
                      (b) (b) (b) 4 1 9 (b) (b) 5
                      (b) (b) (b) (b) 8 (b) (b) 7 9]
        goal-state [5 3 4 6 7 8 9 1 2
                     6 7 2 1 9 5 3 4 8
                     1 9 8 3 4 2 5 6 7
                     8 5 9 7 6 1 4 2 3
                     4 2 6 8 5 3 7 9 1
                     7 1 3 9 2 4 8 5 6
                     9 6 1 5 3 7 2 8 4
                     2 8 7 4 1 9 6 3 5
                     3 5 8 2 8 6 1 7 9]
        max-states 1000000
        costs nil
        came-from (alg/breadth-first-search su/children max-states start-state goal-state)
        ; [came-from costs] (alg/shortest-path np/children (constantly 1)
        ;                                      max-states start-state goal-state )
        ; [came-from costs] (alg/shortest-path np/children np/prefer-horizontal-cost
        ;                                      max-states start-state goal-state)
        path (alg/extract-path came-from start-state goal-state)]
    (print-results came-from path costs goal-state)))

; Run (with timing) with
; (time (-main))
; or
; time lein run
; this is a test for committing to github
