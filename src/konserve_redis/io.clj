(ns konserve-redis.io
  "IO function for interacting with database"
  (:require [taoensso.carmine :as car])
  (:import  [java.io ByteArrayInputStream]))

(set! *warn-on-reflection* 1)

(defn split-header [bytes]
  (when bytes
    (let [data  (->> bytes vec (split-at 4))
          streamer (fn [header data] (list (byte-array header) (-> data byte-array (ByteArrayInputStream.))))]
      (apply streamer data))))

(defn it-exists? 
  [conn id]
  (= (car/wcar conn (car/exists id)) 1)) 
  
(defn get-it 
  [conn id]
  (let [resp (car/wcar conn (car/hmget id "meta" "data"))]
    (doall (map split-header resp))))

(defn get-it-only 
  [conn id]
  (let [resp (first (car/wcar conn (car/hmget id "data")))]
    (split-header resp)))

(defn get-meta
  [conn id]
  (let [resp (first (car/wcar conn (car/hmget id "meta")))]
    (split-header resp)))

(defn update-it 
  [conn id data]
  (car/wcar conn 
    (car/hmset id "meta" (first data) "data" (second data))))

(defn delete-it 
  [conn id]
  (car/wcar conn (car/del id))) 

(defn get-keys 
  [conn]
  (let [keys (car/wcar conn (car/keys "*"))]
    (map #(get-meta conn %) keys)))

(defn raw-get-it-only 
  [conn id]
  (first (car/wcar conn (car/hmget id "data"))))

(defn raw-get-meta 
  [conn id]
  (first (car/wcar conn (car/hmget id "meta"))))
  
(defn raw-update-it-only 
  [conn id data]
  (when data
    (car/wcar conn (car/hmset id "data" data))))

(defn raw-update-meta
  [conn id meta]
  (when meta
    (car/wcar conn (car/hmset id "meta" meta))))
