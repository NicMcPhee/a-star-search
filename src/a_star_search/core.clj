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
        result (alg/breadth-first-search np/children max-states start-state goal-state)
        ; [result costs] (alg/shortest-path np/children max-states start-state goal-state (constantly 1))
        ; [result costs] (alg/shortest-path np/children max-states start-state goal-state prefer-horizontal-cost)
        path (alg/extract-path result start-state goal-state)
        ]
    (print-results came-from path costs goal-state)))

; (time (-main))
; or
; lein run
