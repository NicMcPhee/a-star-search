(ns a-star-search.core
  (:require [search.algorithms :as alg]
            [problems.triangle-LSJZ :as tri])
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
  (let [start-state (tri/->State [[1] [1 1] [1 1 1] [1 1 1 1] [1 1 1 1 0]])
        goal-state (tri/->State [[0] [0 0] [1 0 0] [0 0 0 0] [0 0 0 0 0]])
        max-states 1000000
        costs nil
        ;came-from (alg/breath-first-search tri/children max-states start-state goal-state)
        [came-from costs] (alg/shortest-path tri/children (constantly 1)
                                              max-states start-state goal-state )
        ; [came-from costs] (alg/shortest-path tri/children tri/prefer-horizontal-cost
        ;                                      max-states start-state goal-state)
        path (alg/extract-path came-from start-state goal-state)]
    (print-results came-from path costs goal-state)))

; Run (with timing) with
; (-main)
; or
; time lein run
