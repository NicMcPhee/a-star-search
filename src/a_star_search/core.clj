(ns a-star-search.core
  (:require [search.algorithms :as alg]
            [problems.n-puzzle :as np]
            [problems.sudoku :as su])
  (:gen-class))

(defn print-results [result path costs goal-state]
  (println "The path to the solution is:")
  (doseq [b path]
    (su/print-state b))
  (println "We explored " (count result) " states.")
  (println "The path was " (count path) " steps long.")
  (when costs
    (println "The path has" (count path) "steps and its cost is" (costs goal-state))))

(defn -main
  "Search for a hard-coded sudoku target state."
  [& args]
  (let [start-state [5 3 0 0 7 0 0 0 0
                      6 0 0 1 9 5 0 0 0
                      0 9 8 0 0 0 0 6 0
                      8 0 0 0 6 0 0 0 3
                      4 0 0 8 0 3 0 0 1
                      7 0 0 0 2 0 0 0 6
                      0 6 0 0 0 0 2 8 0
                      0 0 0 4 1 9 0 0 5
                      0 0 0 0 8 0 0 7 9]
        goal-state [5 3 4 6 7 8 9 1 2
                     6 7 2 1 9 5 3 4 8
                     1 9 8 3 4 2 5 6 7
                     8 5 9 7 6 1 4 2 3
                     4 2 6 8 5 3 7 9 1
                     7 1 3 9 2 4 8 5 6
                     9 6 1 5 3 7 2 8 4
                     2 8 7 4 1 9 6 3 5
                     3 4 5 2 8 6 1 7 9]
        max-states 10000
        costs nil
        ;came-from (alg/breadth-first-search su/children max-states start-state goal-state)
        came-from (alg/a-star su/children su/heuristic max-states start-state goal-state)
        ; [came-from costs] (alg/shortest-path np/children (constantly 1)
        ;                                      max-states start-state goal-state )
        ; [came-from costs] (alg/shortest-path np/children np/prefer-horizontal-cost
        ;                                      max-states start-state goal-state)
        path (alg/extract-path came-from start-state goal-state)]
    (print-results came-from path costs goal-state)))
