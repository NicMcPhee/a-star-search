(ns a-star-search.core
  (:require [search.algorithms :as alg]
            [problems.n-puzzle :as np]
            [problems.maze :as mz]
            [clojure.tools.namespace.repl :refer [refresh]])
  (:gen-class))

(defn printable-board [board pos]
  (assoc-in board pos "_"))

(defn print-results [result path costs goal-state]
  (println (str "We explored " (count result) " states."))
  (println "The path, in [row col] format, to the solution is:")
  (doseq [b (map :current-pos path)]
    (println b))
  (when costs
    (println "The path has" (count path) "steps and its cost is" (costs goal-state))))


;;;;  These function may be implemented in a different way in the future. They are being kept here indefinitely... Or until we create a branch for them.

;; (defn make-final-board [positions board]
;;   (println positions)
;;   (make-final-board (rest positions) (printable-board board (first positions))))



;; (defn print-results [result path costs goal-state]
;;   (println (str "We explored " (count result) " states."))
;;   (println "The path, in [row col] format, to the solution is:")
;;   (let [positions (map :current-pos path)]
;;         (println (make-final-board positions mz/test-board)))
;;   (when costs
;;     (println "The path has" (count path) "steps and its cost is" (costs goal-state))))



(defn -main
  "Search for a hard-coded N-puzzle target state."
  [& args]
  (let [start-state (mz/->State mz/test-board [0 0])
        goal-state (mz/->State mz/test-board [15 15])
        max-states 1000000
;;         costs (constantly 3)
;;         came-from (alg/breadth-first-search mz/children max-states start-state goal-state)
;;         [came-from costs] (alg/shortest-path mz/children mz/get-cost
;;                                            max-states start-state goal-state)
;;         came-from (alg/heuristic-search mz/children mz/heuristic start-state goal-state :max-states 1000000)
        [came-from costs] (alg/a-star-search mz/children mz/heuristic mz/get-cost start-state goal-state :max-states 1000000)
        path (alg/extract-path came-from start-state goal-state)]
    (print-results came-from path costs goal-state)))

; Run (with timing) with
(time (-main))
; or
; time lein run
