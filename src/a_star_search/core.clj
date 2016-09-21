(ns a-star-search.core
  (:require [search.algorithms :as alg]
            [problems.triangle-LSJZ :as tri])
  (:gen-class))

(defn print-results [result path costs]
  (println (str "We explored " (count result) " states."))
  (println "The path to the solution is:")
  (doseq [b (map :board path)]
    (println b))
  (when costs
    (println "The path has" (count path) "steps and its cost is" (costs (last path)))))

(defn -main
  "Search for a hard-coded N-puzzle target state."
  [& args]
  (let [start-state (tri/->State [[1] [1 1] [1 1 1] [1 1 1 1] [1 1 1 1 0] [1 1 1 1 1 1]])
        ;goal-state (tri/->State [[0] [0 0] [1 0 0] [0 0 0 0] [0 0 0 0 0]])
        max-states 1000000
        costs nil
        ;came-from (alg/breadth-first-search tri/children max-states start-state tri/winning-board?)
        ;[came-from costs] (alg/shortest-path tri/children (constantly 1)
        ;                                      max-states start-state tri/winning-board? )
        ; [came-from costs] (alg/shortest-path tri/children tri/prefer-horizontal-cost
        ;                                      max-states start-state goal-state)
        [came-from costs] (alg/a-star-search
                            tri/children
                            (constantly 0)
                            tri/heuristic
                            max-states
                            start-state
                            tri/winning-board?)
        path (alg/extract-path came-from start-state tri/winning-board?)]
    (print-results came-from path costs)))

; Run (with timing) with
; (-main)
; or
; time lein run
