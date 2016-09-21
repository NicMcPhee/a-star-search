(ns search.algorithms
  (:require clojure.set
            [clojure.data.priority-map :as pm])
  (:gen-class))

(defn breadth-first-search [children-fn max-states start-state goal-state]
  (loop [max-states max-states
         frontier (conj (clojure.lang.PersistentQueue/EMPTY) start-state)
         visited #{}
         came-from {}]
    (if (or (neg? max-states)
            (empty? frontier)
            (goal-state (peek frontier)))
      came-from
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

(defn shortest-path [children-fn cost-fn max-states start-state goal-state]
  (loop [max-states max-states
         frontier (pm/priority-map start-state 0)
         came-from {}
         cost-so-far {start-state 0}]
    (if (or (neg? max-states)
            (empty? frontier)
            (goal-state (peek frontier)))
      [came-from cost-so-far]
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

(defn a-star-search [children-fn cost-fn heuristic-fn max-states start-state goal-state?]
  (loop [max-states max-states
         frontier (pm/priority-map start-state 0)
         came-from {}
         cost-so-far {start-state 0}]
    (if (or (neg? max-states)
            (empty? frontier)
            (goal-state? (peek frontier)))
      [came-from cost-so-far]
      (let [current (first (peek frontier))
            current-cost (cost-so-far current)
            children (set (children-fn current))
            children-costs (reduce #(assoc %1 %2 (+ current-cost (cost-fn current %2))) {} children)
            children-to-add (filter #(or (not (contains? cost-so-far %))
                                         (< (children-costs %) (cost-so-far %))) children)
            new-cost-so-far (reduce #(assoc %1 %2 (children-costs %2)) cost-so-far children-to-add)
            new-frontier (reduce
                           #(assoc %1 %2 (+ (children-costs %2) (heuristic-fn %2)))
                           (pop frontier)
                           children-to-add) ;; <-----
            new-came-from (reduce #(assoc %1 %2 current) came-from children-to-add)]
        (recur (- max-states (count children-to-add))
               new-frontier
               new-came-from
               new-cost-so-far)))))

(defn extract-path [came-from start-state goal-state?]
  (loop [current-state (first
                         (first
                           (filter #(goal-state? (first %))
                                   came-from)))
         path []]
    (cond
      (nil? current-state) nil
      (= current-state start-state) (reverse (conj path start-state))
      :else (recur (came-from current-state)
                   (conj path current-state)))))


