import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Region } from '../_models/region';

@Injectable({
  providedIn: 'root'
})
export class RegionService {

  private url = "http://localhost:8080";
  private route = "/region";

  constructor(private http: HttpClient) { }

  getRegions() {
    return this.http.get<Region[]>(this.url + this.route);
  }

  createRegion(region: any) {
    return this.http.post(this.url + this.route, region);
  }

  getRegion(id: number) {
    return this.http.get<Region>(this.url + this.route + "/" + id);
  }

  updateRegion(region: any, id: number) {
    return this.http.put(this.url + this.route + "/" + id, region);
  }

  deleteRegion(id: number) {
    return this.http.delete(this.url + this.route + "/" + id);
  }

  activateRegion(id: number) {
    return this.http.put(this.url + this.route + "/" + id + "/activate", null);
  }

  getActiveRegions() {
    return this.http.get<Region[]>(this.url + this.route + "/active");
  }

}