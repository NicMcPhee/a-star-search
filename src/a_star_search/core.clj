(ns a-star-search.core
  (:require [search.algorithms :as alg]
            ;[problems.n-puzzle :as np]
            [problems.shinny-sam-sudoku :as s])
  (:gen-class))

(defn print-results [result path costs goal-state]
  (println (str "We explored " (count result) " states."))
  (println "The path to the solution is:")
  (doseq [b (map :board path)]
    (println b))
  (println "The path has" (count path) "steps.")
  (when costs
    (println "Its cost is" (costs goal-state))))


(defn -main
  "Search for a hard-coded sudoku target state."
  [& args]
  (let [start-state (s/->State [  [0 0 1 9 7 3 0 5 6]
                                  [0 9 0 0 8 0 0 0 0]
                                  [0 0 0 0 0 0 3 0 0]

                                  [5 0 0 0 0 1 0 0 0]
                                  [0 0 0 0 0 0 0 4 0]
                                  [4 7 0 3 0 0 1 9 2]

                                  [0 0 0 0 4 0 0 0 0]
                                  [3 0 2 0 0 5 6 0 0]
                                  [1 0 0 0 0 0 0 0 0]] [0, 0] )



        goal-state (s/->State [   [8 4 1 9 7 3 2 5 6]
                                  [6 9 3 5 8 2 4 1 7]
                                  [7 2 5 6 1 4 3 8 9]

                                  [5 3 9 4 2 1 7 6 8]
                                  [2 1 8 7 6 9 5 4 3]
                                  [4 7 6 3 5 8 1 9 2]

                                  [9 5 7 2 4 6 8 3 1]
                                  [3 8 2 1 9 5 6 7 4]
                                  [1 6 4 8 3 7 9 2 5]] [9, 0] )



        max-states 1000000
        costs nil
        ;came-from (alg/breadth-first-search s/children max-states start-state goal-state)
        ;[came-from costs] (alg/shortest-path s/children (constantly 1)
        ;                                      max-states start-state goal-state )
        ;[came-from costs] (alg/shortest-path s/children s/prefer-horizontal-cost
        ;                                      max-states start-state goal-state)
        came-from (alg/heuristic-search s/children s/avg-test start-state goal-state :max-states 10000)
        path (alg/extract-path came-from start-state goal-state)]
    (print-results came-from path costs goal-state)))




; Run (with timing) with
;(time (-main))
; or
; time lein run
