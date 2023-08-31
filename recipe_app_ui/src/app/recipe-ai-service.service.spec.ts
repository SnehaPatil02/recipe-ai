import { TestBed } from '@angular/core/testing';

import { RecipeAiServiceService } from './recipe-ai-service.service';

describe('RecipeAiServiceService', () => {
  let service: RecipeAiServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(RecipeAiServiceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
