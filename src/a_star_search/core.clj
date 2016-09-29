(ns a-star-search.core
  (:require [search.algorithms :as alg]
            [problems.hanoi :as np])
  (:gen-class))


;;This visual displays the steps that were taken to the fuction.
(defn print-results [result path costs goal-state]
  (println (str "We explored " (count result) " states."))
  (println "The path to the solution is:")
  (doseq [b (map :towers path)]
    (println b))
  (when costs
    (println "The path has" (count path) "steps and its cost is" (costs goal-state))))


(defn -main
  "Search for a hard-coded Towers of Hanoi target state."
  [& args]
  (let [start-state (np/->State [[5 4 3 2 1] [] []])
        goal-state (np/->State [[] [] [5 4 3 2 1]])
        max-states 1000000
        costs 1
;;          came-from (alg/breadth-first-search np/children max-states start-state goal-state)
;;          [came-from costs] (alg/shortest-path np/children (constantly 1)
;;                                           max-states start-state goal-state )
;;          [came-from costs] (alg/shortest-path np/children np/prefer-horizontal-cost
;;                                            max-states start-state goal-state)
        came-from (alg/heuristic-search np/children np/spicy start-state goal-state :max-states 10000)
        path (alg/extract-path came-from start-state goal-state)]
    (print-results came-from path costs goal-state)))

; Run (with timing) with

(time (-main))
