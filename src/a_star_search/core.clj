(ns a-star-search.core
  (:require [search.algorithms :as alg]
            [problems.n-puzzle :as np])
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
  (let [start-state (np/->State [[0 1 3] [4 2 5] [7 8 6]] [0 0])
        goal-state (np/->State [[0 1 2] [3 4 5] [6 7 8]] [0 0])
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
; this is a test for committing to github

