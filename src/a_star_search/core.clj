(ns a-star-search.core
  (:require [search.algorithms :as alg]
            [problems.n-puzzle :as np]
            [problems.towers :as towers])
  (:gen-class))

(defn print-results [result path costs goal-state]
  (println (str "We explored " (count result) " states."))
  (println "The path to the solution is:")
  (doseq [b path]
    (towers/cool-print-runnings b))
  (when costs
    (println "Its cost is" (costs goal-state))))

(defn -main
  "Search for a hard-coded N-puzzle target state."
  [& args]
  (let [start-state towers/start-state
        goal-state towers/goal-state
        max-states 1000000
        costs nil
        ;came-from (alg/breadth-first-search towers/children max-states start-state goal-state)
        ;[came-from costs] (alg/shortest-path towers/children (constantly 1)
        ;                                     max-states start-state goal-state )
        ; [came-from costs] (alg/shortest-path np/children np/prefer-horizontal-cost
        ;                                      max-states start-state goal-state)
        ;came-from (alg/heuristic-search towers/children  towers/fitness towers/start-state towers/goal-state :max-states 10000)
	came-from (alg/a-star towers/children towers/fitness towers/start-state towers/goal-state (fn [x y] 1))
	;came-from (alg/a-star "boop" "boop" "boop" "boop")
        path (alg/extract-path came-from start-state goal-state)]
    (print-results came-from path costs goal-state)))

; Run (with timing) with
; (time (-main))
; or
; time lein run
