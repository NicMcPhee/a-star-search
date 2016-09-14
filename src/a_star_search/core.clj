(ns a-star-search.core
  (:require [search.algorithms :as alg]
            [problems.n-puzzle :as np]
            [problems.shinny-sam-sudoku :as s])
  (:gen-class))

(defn print-results [result path costs goal-state]
  (println (str "We explored " (count result) " states."))
  (println "The path to the solution is:")
  (doseq [b (map :board path)]
    (println b))
  (when costs
    (println "The path has" (count path) "steps and its cost is" (costs goal-state))))


(defn -main
  "Search for a hard-coded sudoku target state."
  [& args]
  (let [start-state (s/->State [ [[0 0 0 2 6 0 7 0 1]
                                  [6 8 0 0 7 0 0 9 0]
                                  [1 9 0 0 0 4 5 0 0]

                                  [8 2 0 1 0 0 0 4 0]
                                  [0 0 4 6 0 2 9 0 0]
                                  [0 5 0 0 0 3 0 2 8]

                                  [0 0 9 3 0 0 0 7 4]
                                  [0 4 0 0 5 0 0 3 6]
                                  [7 0 3 0 1 8 0 0 0]] [0, 0] ])



        goal-state (s/->State [  [[4 3 5 2 6 9 7 8 1]
                                  [6 8 2 5 7 1 4 9 3]
                                  [1 9 7 8 3 4 5 6 2]

                                  [8 2 6 1 9 5 3 4 7]
                                  [3 7 4 6 8 2 9 1 5]
                                  [0 5 0 7 4 3 6 2 8]

                                  [5 1 9 3 2 6 8 7 4]
                                  [2 4 8 9 5 7 1 3 6]
                                  [7 6 3 4 1 8 2 5 9]] [8, 8] ])



        max-states 1000000
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
