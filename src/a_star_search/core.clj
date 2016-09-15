(ns a-star-search.core
  (:require [search.algorithms :as alg]
            [problems.sudoku-Leuni :as np])
  (:gen-class))

(defn print-results [result path costs goal-state]
  (println (str "We explored " (count result) " states."))
  (println "The path to the solution is:")
  (doseq [b (map :board path)]
    (println b))
  (when costs
    (println "The path has" (count path) "steps and its cost is" (costs goal-state))))

(defn -main
  "Search for a hard-coded N-puzzle target state."
  [& args]
  (let [start-state (np/->State [[0 7 5 0 9 0 0 0 6]
                                 [0 2 3 0 8 0 0 4 0]
                                 [8 0 0 0 0 3 0 0 1]
                                 [5 0 0 7 0 2 0 0 0]
                                 [0 4 0 8 0 6 0 2 0]
                                 [0 0 0 9 0 1 0 0 3]
                                 [9 0 0 4 0 0 0 0 7]
                                 [0 6 0 0 7 0 5 8 0]
                                 [7 0 0 0 1 0 3 9 0]] [0 0])

        goal-state (np/->State [ [1 7 5 2 9 4 8 3 6]
                                 [6 2 3 1 8 7 9 4 5]
                                 [8 9 4 5 6 3 2 7 1]
                                 [5 1 9 7 3 2 4 6 8]
                                 [3 4 7 8 5 6 1 2 9]
                                 [2 8 6 9 4 1 7 5 3]
                                 [9 3 8 4 2 5 6 1 7]
                                 [4 6 1 3 7 9 5 8 2]
                                 [7 5 2 6 1 8 3 9 4]] [8 8])
        max-states 1000000000
        costs nil
        came-from (alg/breadth-first-search np/children max-states start-state goal-state)
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
