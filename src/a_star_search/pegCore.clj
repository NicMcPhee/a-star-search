(ns a-star-search.pegCore
  (:require [search.algorithmsPeg :as alg]
            [problems.peg-puzzle :as np])
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
  (let [start-state (np/->State [0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1] 5) ;START-STATE: addition of array= array.legnth-1
        goal-state (np/->State [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0] 5) ; END-STATE: addition of array = 1
        max-states 1000000
        costs nil
        ;came-from (alg/breadth-first-search np/children max-states start-state goal-state)
        ;[came-from costs] (alg/shortest-path np/children (constantly 1)
        ;                                    max-states start-state goal-state )
        ;; NOTE: if "constantly 1" is changed to "constantly 0" in shortest-path AND
        ;; in a-star, both are improved significantly... we don't know why!
        ; [came-from costs] (alg/shortest-path np/children np/prefer-horizontal-cost
        ;                                      max-states start-state goal-state)
         [came-from costs] (alg/a-star np/heuristic np/children (constantly 1)
                                              max-states start-state goal-state )
        path (alg/extract-path came-from start-state goal-state)]
    (print-results came-from path costs goal-state)))

; Run (with timing) with
; (time (-main))
; or
; time lein run
