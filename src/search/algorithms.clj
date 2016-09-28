(ns search.algorithms
  (:require clojure.set
            [clojure.data.priority-map :as pm]
            [problems.peg-game :as pg])
  (:gen-class))



(defn breadth-first-search [children-fn max-states start-state is-true]
  (loop [max-states max-states
         frontier (conj (clojure.lang.PersistentQueue/EMPTY) start-state)
         visited #{}
         came-from {}]
    (if (or (neg? max-states)
            (empty? frontier)
            (is-true (peek frontier)))
      [came-from (peek frontier)]
      (let [current (peek frontier)
            children (set (children-fn current))
            unvisited-children (clojure.set/difference children visited)
            new-frontier (reduce conj (pop frontier) unvisited-children)
            new-visited (clojure.set/union children visited)
            new-came-from (reduce #(assoc %1 %2 current) came-from unvisited-children)]
        (recur (- max-states (count unvisited-children))
               new-frontier
               new-visited
               new-came-from)))))

(defn shortest-path [children-fn cost-fn max-states start-state is-true]
  (loop [max-states max-states
         frontier (pm/priority-map start-state 0)
         came-from {}
         cost-so-far {start-state 0}]
    (if (or (neg? max-states)
            (empty? frontier)
            (is-true (first (peek frontier))))
      [came-from cost-so-far (first (peek frontier))]
      (let [current (first (peek frontier))
            current-cost (cost-so-far current)
            children (set (children-fn current))
            children-costs (reduce #(assoc %1 %2 (+ current-cost (cost-fn current %2))) {} children)
            children-to-add (filter #(or (not (contains? cost-so-far %))
                                         (< (children-costs %) (cost-so-far %))) children)
            new-cost-so-far (reduce #(assoc %1 %2 (children-costs %2)) cost-so-far children-to-add)
            new-frontier (reduce #(assoc %1 %2 (children-costs %2)) (pop frontier) children-to-add)
            new-came-from (reduce #(assoc %1 %2 current) came-from children-to-add)]
        (recur (- max-states (count children-to-add))
               new-frontier
               new-came-from
               new-cost-so-far)))))


;; This does not work currently our goal-state is not actually a goal state. It just checks to see if we are at a goal state then returns true if it is a goal state
;;
;; (defn heuristic-search [children-fn heuristic-fn start-state is-true & {:keys [max-states] :or {max-states 1000000}}]
;;   (loop [frontier (pm/priority-map start-state 0)
;;          came-from {}
;;          visited #{}]
;;     (if (or (empty? frontier)
;;             (>= (count came-from) max-states)
;;             (is-true (first (peek frontier))))
;;       [came-from (first (peek frontier))]
;;       (let [current (first (peek frontier))
;;             children (set (children-fn current))
;;             unvisited-children (clojure.set/difference children visited)
;;             heuristics (map (partial heuristic-fn goal-state) unvisited-children)
;;             new-frontier (reduce #(assoc %1 %2 (heuristic-fn goal-state %2)) (pop frontier) unvisited-children)
;;             new-came-from (reduce #(assoc %1 %2 current) came-from unvisited-children)
;;             new-visited (clojure.set/union children visited)]
;; (recur new-frontier new-came-from new-visited)))))



(defn A* [children-fn cost-fn max-states start-state is-true]
  (loop [max-states max-states
         frontier (pm/priority-map start-state 0)
         came-from {}
         cost-so-far {start-state 0}]
    (if (or (neg? max-states)
            (empty? frontier)
            (is-true (first (peek frontier))))
      [came-from cost-so-far (first (peek frontier))]
      (let [current (first (peek frontier))
            current-cost (cost-so-far current)
            children (set (children-fn current))
            children-costs (reduce #(assoc %1 %2 (+ current-cost (cost-fn current %2))) {} children)
            children-to-add (filter #(or (not (contains? cost-so-far %))
                                         (< (children-costs %) (cost-so-far %))) children)
            new-cost-so-far (reduce #(assoc %1 %2 (+ (children-costs %2) (pg/heuristic current %2))) cost-so-far children-to-add)
            new-frontier (reduce #(assoc %1 %2 (children-costs %2)) (pop frontier) children-to-add)
            new-came-from (reduce #(assoc %1 %2 current) came-from children-to-add)]
        (recur (- max-states (count children-to-add))
               new-frontier
               new-came-from
               new-cost-so-far)))))

(defn extract-path [came-from start-state goal-state]
  (loop [current-state goal-state
         path []]
    (cond
      (nil? current-state) nil
      (= current-state start-state) (reverse (conj path start-state))
      :else (recur (came-from current-state)
                   (conj path current-state)))))
